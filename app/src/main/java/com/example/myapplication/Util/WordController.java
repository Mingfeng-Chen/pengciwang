package com.example.myapplication.Util;

import android.util.Log;

import com.example.myapplication.config.ConfigData;
import com.example.myapplication.domain.Interpretation;
import com.example.myapplication.domain.Word;
import com.example.myapplication.domain.UserConfig;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*背单词流程的控制器*/
public class WordController {
    // 今天复习总共的单词量
    public static int wordReviewNum;
    // 今天学习总共的单词量
    public static int wordLearnNum;

    // 当前ID
    public static int currentWordId;

    public static final String TAG = "WordController";

    // 候选需要学习的单词
    public static List<Integer> needLearnWords = new ArrayList<>();

    // 候选需要复习的单词
    public static List<Integer> needReviewWords = new ArrayList<>();

    // 用于存放本轮刚学习过的单词，以便及时复习
    public static List<Integer> justLearnedWords = new ArrayList<>();

    // 生成候选需学习的单词
    public static void generateDailyLearnWords(long lastStartTime) {

        needLearnWords.clear();
        justLearnedWords.clear();

        // 获得准备数据
        List<UserConfig> userConfigs = LitePal.where("userId = ?", ConfigData.getUserNumLogged() + "").find(UserConfig.class);
        // 需要学习的，但是并没有在指定时间去学习
        List<Word> wordNeedLearnList = LitePal.where("isNeedLearned = ?", "1")
                .select("wordId").find(Word.class);
        // 根本没有学习过的单词（这是供下面用来分配单词的库）
        // 不需要学习的单词有可能是需要复习的单词，需要将其排除
        List<Word> wordNoNeedLearnList = LitePal.where("isNeedLearned = ? and isLearned = ?", "0", "0").select("wordId").find(Word.class);
        // 得到每天需要的学习量
        int needWordTotal = userConfigs.get(0).getWordNeedReciteNum();
        wordLearnNum = needWordTotal;
        // 说明再点击按钮的时候已经是新的一天了，这时候就要重新分配单词了
        if (!TimeController.isTheSameDay(lastStartTime, TimeController.getCurrentTimeStamp())) {
            Log.d(TAG, "a new day");
            // 分情况
            // 情况1.说明需要的单词是差了一些的，需要再分配一点
            if (wordNeedLearnList.size() < needWordTotal) {
                // 说明需要再分配differ个学习的单词
                int differ = needWordTotal - wordNeedLearnList.size();
                Log.d(TAG, "wordNoNeedLearnList=" + wordNoNeedLearnList.size());
                // 在未学习的列表里随机分配
                int[] stillLearnList = NumberController.getRandomNumberList(0, wordNoNeedLearnList.size() - 1, differ);
                Log.d(TAG, "still" + Arrays.toString(stillLearnList));
                // 把这些数据划给候选单词列表
                // 并且给它们标记为需要学习
                for (int i : stillLearnList) {
                    needLearnWords.add(wordNoNeedLearnList.get(i).getWordId());
                    // 更新数据
                    Word word = new Word();
                    word.setIsNeedLearned(1);
                    word.updateAll("wordId = ?", wordNoNeedLearnList.get(i).getWordId() + "");
                }
                // 最后把之前需要学习但是在规定时间未学习的单词也一并划入候选列表中
                if (!wordNeedLearnList.isEmpty()) {
                    for (Word word : wordNeedLearnList) {
                        needLearnWords.add(word.getWordId());
                    }
                }
            } else {
                // 说明之前需要学习但是在规定时间未学习的单词量已经足够今天所学习了，不需要再分配更多了，直接把之前的再拿来用就可以了
                int i = 0;
                for (Word word : wordNeedLearnList) {
                    ++i;
                    if (i <= needWordTotal)
                        needLearnWords.add(word.getWordId());
                    else
                        break;
                }
            }
        } else {
            Log.d(TAG, "the same day");
            // 这时候说明还是同一天，直接分配未学习的单词就可以了
            int i = 0;
            for (Word word : wordNeedLearnList) {
                ++i;
                if (i <= needWordTotal)
                    needLearnWords.add(word.getWordId());
                else
                    break;
            }
        }
        Log.d(TAG, "generateDailyLearnWords: ");
        Log.d(TAG, needLearnWords.toString());
    }

    // 生成候选需复习的单词
    // 这就不需要判断是否是当天了，因为每天复习的列表都是一致的
    public static void generateDailyReviewWords() {
        Log.d(TAG, "开始运行了");
        // 防止重复添加
        needReviewWords.clear();
        justLearnedWords.clear();
        // 获得准备数据
        // 获取曾经学过的需要复习的单词
        List<Word> needReviewBeforeList = LitePal.where("isLearned = ?", "1").select("wordId").find(Word.class);
        //把以前学习的单词加入到候选复习单词列表
        for (Word word : needReviewBeforeList){
            needReviewWords.add(word.getWordId());
        }
        Log.d(TAG, "generateDailyReviewWords: ");
        Log.d(TAG, needReviewWords.toString());
    }

    /**
     * 找到需要学习的新单词
     * @return 从需要学习的单词列表中随机选出单词的wordId
     */
    public static int learnNewWord() {
        Log.d(TAG, "learnNewWord--------------------------------------------------------");
        Log.d(TAG, "learnNewWord" + "最后是我返回了值");
        Log.d(TAG, "needLearnedWords.size=" + needLearnWords.size());
        if (!needLearnWords.isEmpty()) {
            int index = NumberController.getRandomNumber(0, needLearnWords.size() - 1);
            return needLearnWords.get(index);
        } else
            return -1;
    }

    /**
     * 通过wordId，将该单词的学习状态变为已经学习
     * @param wordId
     */
    public static void learnNewWordDone(int wordId) {
        Log.d(TAG, "learnNewWordDone--------------------------------------------------------");
        // 移除
        Log.d(TAG, "before　size:" + needLearnWords.size());
        for (int i = 0; i < needLearnWords.size(); ++i) {
            if (needLearnWords.get(i) == wordId) {
                needLearnWords.remove(i);
                break;
            }
        }
        Log.d(TAG, "after size:" + needLearnWords.size());
        // 放进临时需要复习的列表里
        justLearnedWords.add(wordId);
        // 更新数据库数据
        Word word = new Word();
        word.setIsLearned(1);
        word.setToDefault("isNeedLearned");
        word.updateAll("wordId = ?", wordId + "");
    }

    /**
     * 找到需要复习的刚刚学习的单词
     * @return 从刚刚学习的单词列表中随机选出单词的wordId
     */
    public static int reviewNewWord() {
        Log.d(TAG, "reviewNewWord: 最后是我返回了值");
        if (!justLearnedWords.isEmpty()) {
            Log.d(TAG, "-1=?" + justLearnedWords.size());
            int index = NumberController.getRandomNumber(0, justLearnedWords.size() - 1);
            return justLearnedWords.get(index);
        } else
            return -1;
    }

    /**
     *通过wordId，将一个单词从刚刚学习过的单词复习列表中去除
     * @param wordId
     */
    public static void reviewNewWordDone(int wordId) {
        // 移除对应单词
        for (int i = 0; i < justLearnedWords.size(); ++i) {
            if (justLearnedWords.get(i) == wordId) {
                justLearnedWords.remove(i);
                break;
            }
        }
    }

    /**
     * 找到需要复习的以前学习的单词
     * @return 从以前学习的单词列表中随机选出单词的wordId
     */
    public static int reviewOldWord() {
        Log.d(TAG, "reviewOldWord: 最后是我返回了值");
        if (!needReviewWords.isEmpty()) {
            int index = NumberController.getRandomNumber(0, needReviewWords.size() - 1);
            return needReviewWords.get(index);
        } else
            return -1;
    }

    /**
     *通过wordId，将一个单词从以前学习过的单词复习列表中去除
     * @param wordId
     */
    public static void reviewOldWordDone(int wordId) {
        // 移除对应单词
        for (int i = 0; i < needReviewWords.size(); ++i) {
            if (needReviewWords.get(i) == wordId) {
                needReviewWords.remove(i);
                break;
            }
        }
    }

    /**
     * 通过wordId获得对应单词释义
     * @param wordId
     * @return
     */
    public static String getWordInterpretation(int wordId){
        // 得到该单词的释义
        List<Interpretation> interpretations = LitePal.where("wordId = ?", wordId + "").find(Interpretation.class);
        StringBuilder meaning = new StringBuilder();
        for(Interpretation i : interpretations){
            if (!interpretations.isEmpty()) {
                meaning.append(i.toString()+" ");
            }

        }
        return meaning.toString();
    }
}
