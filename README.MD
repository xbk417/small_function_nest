        本项目中包含有四个程序，分别对应AAA数据解密、固定大小输出、GBK转UTF8、字段数统计等四个功能。
        项目中目录对应情况：
                com.bonc.functions.aaa：AAA数据解密
                com.bonc.functions.fixedoutput：固定大小输出
                com.bonc.functions.gbk2utf8：GBK转UTF8
                com.bonc.functions.statistic：字段数统计

## 1. AAA数据解密 ##
#### 功能 ####
        对AAA数据进行解密，默认是Base64解密。
        可实现其他解密方法，只需实现com.bonc.functions.aaa.decrypt.IDecrypt接口，并通过配置文件中‘aaa.data.decrypt.class’配置相应解密类即可。
#### 程序使用说明 ####
        程序主类为：com.bonc.functions.aaa.AAADealMain
        程序参数列表为：
                -inputPath：输入路径
                -outputPath：输出路径
                -configPath：配置文件路径
                -jobName：任务名
                -reduceNum：reduce数

## 2. 固定大小输出 ##
#### 功能 ####
        实现输出文件的固定大小输出，即合并输入文件。
#### 程序使用说明 ####
        程序主类为：com.bonc.functions.fixedoutput.FixedOutputMain
        程序参数列表为：
                -inputPath：输入路径
                -outputPath：输出路径
                -configPath：配置文件路径
                -jobName：任务名

## 3. GBK转UTF8 ##
#### 功能 ####
        实现输出文件由GBK编码转为UTF8编码。
#### 程序使用说明 ####
        程序主类为：com.bonc.functions.gbk2utf8.GBK2UTF8Main
        程序参数列表为（空格分割）：
                输入路径 输出路径 任务名

## 4. 字段数统计 ##
#### 功能 ####
        实现输出文件中每行数据的字段数统计。
#### 程序使用说明 ####
        程序主类为：com.bonc.functions.statistic.DataStatisticMain
        程序参数列表为：
                -inputPath：输入路径
                -outputPath：输出路径
                -configPath：配置文件路径
                -jobName：任务名
                -reduceNum：reduce数
                -flag：统计数据标识