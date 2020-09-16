# pcr-team

基于 `bigfun` 的数据和 `mirai` 框架实现的自动报刀机器人

* 使用 sqlite 作为数据库
* 内嵌 [mirai-core](https://github.com/mamoe/mirai) 作为机器人框架

由于内嵌 `mirai-core` 会需要一堆 kotlin 的包，我也使用了一堆框架增加开发效率（Spring 全家桶、Mybatis 等），导致程序的打包体积非常大。

当前只支持单个公会单个群内的自动报刀。

**本程序没有正式打包发布，推荐有 Java 编程基础的人自行编译修改使用。（主要是公会战还没有开，程序从未正式投入使用，相关测试都是通过上期会战做的）**

## 使用说明

### 环境要求

  * JDK 1.8 或者 JRE 1.8
  * Windows 或 Linux 都可以使用

### 配置

使用前要先修改 `application.yml` 和 `secrets.yml` 的各类配置。

如果需要在催刀的时候自动 @ 对应群员，需要把QQ号对应游戏昵称的关系写入 `members.csv` 。

所有出刀数据都会保存在 `data.db`

### 启动

Windows 使用 CMD 或 PowerShell 执行 `java -jar pcr-team.jar`。

Linux 执行 `java -jar pcr-team.jar &`，加 `&` 是为了在后台运行。


## 许可证

延续 [mirai](https://github.com/mamoe/mirai) 的 AGPLv3 协议，且不鼓励，不支持一切商业使用。
