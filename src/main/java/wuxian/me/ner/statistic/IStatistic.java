package wuxian.me.ner.statistic;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wuxian on 28/7/2017.
 * 在处理好的切词基础上进行数据统计
 */
public interface IStatistic<T> {

    //统计某个item的出现次数,并排序
    Iterator<IModel<T>> sortAndCount(List<T> dataList);

    interface IModel<T> {

        T getKey();

        Integer getSize();

        Iterator<Integer> getPositions();
    }
}