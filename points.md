Elasticsearch：
1.面向文档型数据库
2.ES->索引（Index）->类型（Type）->文档（Docments）->字段（Fields）

一.Elasticsearch索引的精髓：一切设计都是为了提高搜索的性能
参考：
    https://www.infoq.cn/article/database-timestamp-02/?utm_source=infoq&utm_medium=related_content_link&utm_campaign=relatedContent_articles_clk
    https://www.cnblogs.com/dreamroute/p/8484457.html
关键词：
1.倒排索引
2.term dictionary
3.term index
4.FST（finite state transducers）
5.skip list
6.bitset
7.Frame Of Reference（增量编码压缩，将大数变小数，按字节存储）

二.Elasticsearch的索引思路:
    将磁盘里的东西尽量搬进内存，减少磁盘随机读取次数(同时也利用磁盘顺序读特性)，
    结合各种奇技淫巧的压缩算法，用及其苛刻的态度使用内存。

三.对于使用Elasticsearch进行索引时需要注意:
    1.不需要索引的字段，一定要明确定义出来，因为默认是自动建索引的
    2.对于String类型的字段，不需要analysis(分词)的也需要明确定义出来，因为默认也是会analysis的
    3.选择有规律的ID很重要，随机性太大的ID(比如java的UUID)不利于查询。如果ID是顺序的，
    或者是有公共前缀等具有一定规律性的ID，压缩比会比较高；最后通过Posting list里的ID到磁盘中查找Document
    信息的那步，因为Elasticsearch是分Segment存储的，根据ID这个大范围的Term定位到Segment的效率直接影响了
    最后查询的性能，如果ID是有规律的，可以快速跳过不包含该ID的Segment，从而减少不必要的磁盘读次数
