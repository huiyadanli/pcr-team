# pcr-team

基于 `bigfun` 的数据和 `mirai` 框架实现的自动报刀机器人

* 使用 sqlite 作为数据库
* 内嵌 [mirai-core](https://github.com/mamoe/mirai) 作为机器人框架

由于内嵌 `mirai-core` 会需要一堆 kotlin 的包，我也使用了一堆框架增加开发效率（Spring 全家桶、Mybatis 等），导致程序的打包体积非常大。

当前只支持单个公会单个群内的自动报刀、催刀、boss状态查询等功能，所有数据来源于 `bigfun`，每5分钟更新一次。

## 使用说明

### 下载

[点击下载最新release版本]()

### 环境要求

  * JDK 1.8 或者 JRE 1.8
  * Windows 或 Linux 都可以使用（QQ机器人需要挂一段时间，才不会被风控，所以建议使用 Windows 服务器）

### 配置

使用前要先修改 `config\secrets.yml` 的各类配置（内有注释）；

⚠️ 请不要泄露 `secrets.yml` 配置文件给其他人，里面都是账号相关的敏感信息，bigfun cookie 的泄露可能会牵连B站账号。

`config\application.yml` 保持默认配置即可，当然也可以根据注释进行适当修改。

催刀功能需要有公会所有成员游戏昵称和QQ号的列表，如果要使用此功能，请把公会所有成员QQ号对应游戏昵称的关系写入 `db\members.csv` 。

所有出刀数据都会保存在 `db\data.db`

### 启动

Windows 下直接双击 `start.bat`即可（也可以使用 CMD 或 PowerShell 在当前文件夹下执行 `java -jar pcr-team.jar`）。

Linux 执行 `java -jar pcr-team.jar &`，加 `&` 是为了在后台运行。

如果出现内存不够的情况可以自行添加 JVM 参数调整启动内存。

## 可用指令

群内发送以下消息可以触发操作：

- 状态
    - 显示当前boss状态（周目、血量）

- 出刀统计
    - 回复：当前已出[n]刀，剩余[90-n]刀。

- 催刀
    - @未出刀的群员，注意一天@人数过多，后面再@时消息会发不出去 （需要配置公会所有成员游戏昵称对应QQ号的列表：`members.csv`）
    
- 更新出刀数据
    - 手动更新出刀数据，不建议使用

## 许可证

延续 [mirai](https://github.com/mamoe/mirai) 的 AGPLv3 协议，且不鼓励，不支持一切商业使用。
