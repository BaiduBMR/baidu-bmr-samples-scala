# Baidu BMR Spark + HBase 样例程序

> 本样例程序演示了如何在BMR环境下通过Spark APP来读取HBase集群里面的数据并进行进一步的计算的过程。

运行样例程序前，请仔细阅读以下说明：

### 一、准备BMR集群
#### 1. 创建HBase集群
进入百度开放云BMR管理控制台（http://console.bce.baidu.com/bmr/#/bmr/list ），点击“创建集群”按钮，进入集群创建页面，填写好集群的基本配置信息并完成所有创建步骤。

**注意:在软件配置设置一栏内，镜像类型需要选为hadoop,并且添加HBase应用**

#### 2. 创建Spark集群
进入百度开放云BMR管理控制台（http://console.bce.baidu.com/bmr/#/bmr/list ），点击“创建集群”按钮，进入集群创建页面，填写好集群的基本配置信息并完成所有创建步骤。

**注意:在软件配置设置一栏内，镜像类型需要选为Spark**

***注意：创建好HBase集群和Spark集群后，您会通过账号绑定的手机接收到集群创建成功的短信，短信内会有集群Master节点的IP以及SSH账号密码。***

#### 3. 配置Spark集群的/etc/hosts文件

因为在这个样例程序中，需要通过Spark集群访问HBase集群上的数据，所以，我们需要把HBase集群Master节点的/etc/hosts下的配置（127.0.0.1 localhost一栏除外）添加到到Spark集群的所有节点上（Master 和 Slave节点，Slave节点可以经由Master跳转登陆）

### 二、准备样例程序

#### 1. 软件环境
如果需要在您的开发机器上编译运行本样例程序，需要您的机器具备以下的软件环境：

* sbt 0.13.7

* scala 2.10.4

* spark 1.4.1

* hbase 0.98.0-hadoop2

* Scala IDE

#### 2. 编译打包样例代码并上传至BOS上

* Fork或Clone baidu-bmr-samples-scala项目

> git clone https://github.com/BaiduBMR/baidu-bmr-samples-scala.git
	
* cd至baidu-bmr-samples-scala/bmr-spark-hbase-sample目录下，运行"sbt eclipse"生成Eclipse项目文件，然后将项目导入至scala ide内；

* 修改src/main/resources/common.properties文件里面的HBase相关的配置，并运行“sbt assembly"打包生成jar文件：bmr-spark-hbase-sample.jar. 这个jar文件就是样例程序的可执行文件。

* 打开开放云BOS对象存储的管理页面（http://console.bce.baidu.com/bos/#/bos/list ），我们把bmr-spark-hbase-sample.jar上传至某个目录下。

### 三、准备测试表格和测试数据

本样例程序需要访问HBase中一张名为“spark_test_table”并且包含column family “t”的的表.所以在跑示例程序之前，我们需要人工创建这样表,并添加一些简单的数据：

> create ‘spark_test_table','t'

> put 'spark_test_table', "test\xef\xff", 't', "\x01\x33\x40"

测试表和数据准备完毕后，接下来就可以运行样例程序了。

### 四、执行样例程序

在BMR中，可以有两种方式来执行一个作业：1、通过web页面 和 2、通过命令行。注：如果您对集群的配置和管理不太熟悉，建议您优先考虑通过web页面来提交和管理作业。

#### 1. 通过web页面提交并执行样例程序

进入百度开放云BMR管理控制台（http://console.bce.baidu.com/bmr/#/bmr/list ）， 找到上述步骤中创建的Spark集群，进入集群实例详情页面，并选择“作业”一栏。
点击页面中的“添加作业”按钮，在“名称”一栏内填写任意名称，在“bos输入地址”一栏选择上面步骤中我们上传的bmr-spark-hbase-sample.jar在bos上的完整路径（在上面的步骤中，我们已经把bmr-spark-hbase-sample.jar文件上传到BOS上了）。
然后在"参数"一栏填写“ --class com.baidu.bce.bmr.spark.HBaseRDDSample ”（其他未提及的项保持默认即可），然后点击“确定”按钮。这时候，作业将开始调度并执行，我们可以通过作业管理页面来观察作业的执行结果。

#### 2. 通过命令行提交并执行样例程序

* SSH 至spark集群的Master节点，执行“su hdfs” 切换至hdfs用户。

* 上传 bmr-spark-hbase-sample.jar至某个目录下，并在相同的目录执行下面的命令：

> spark-submit --deploy-mode cluster  --master  yarn-cluster --class com.baidu.bce.bmr.spark.HBaseRDDSample bmr-spark-hbase-sample.jar

正常情况下，程序会顺利结束，您可以通过yarn logs -applicationId xxxx  来查看程序的日志信息。


以上就是完整的操作流程，如果您在执行的过程中遇到任何的问题，或者有任何的建议，欢迎联系我们：。
