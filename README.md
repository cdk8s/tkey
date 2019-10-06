
## Introduction

- **TKey = Token Key** 以 OAuth 2.0 标准为接口设计原则的单点登录系统（SSO）
- **初衷：**
    - 做国内各种登录场景的配件，以完善的学习资料为主核心竞争力
    - 希望让有 1 ~ 2 年工作经验的人都可以用 TKey 进行自定义扩展需求
- 纯粹的 HTTP，任意设备、任意场景
- 跨域无状态，随意横向扩展，服务高可用
- Spring Boot 2.1.x 技术栈，没有封装，有一点点基础即刻魔改（MIT License）
- 从开发、测试、部署、监控、前后端分离的材料都已具备
- **最后：没有哪个框架、系统可以套用在任意用户、任意场景、任意需求上，希望对你有思路帮助**

## Git

- Github：<https://github.com/cdk8s/tkey>
- Gitee：<https://gitee.com/cdk8s/tkey>

## Live Demo

![登陆完整过程](http://img.gitnavi.com/tkey/tkey-sso-login.gif)

- **注意：带宽只有 1M，访问会慢** 
- 本地添加下面 hosts 才能正常访问和重定向到登录页面（必备）

```
182.61.44.40 sso.cdk8s.com
```

- 访问地址：[TKey SSO Client Management](http://sso.cdk8s.com/tkey-sso-client-management-frontend/TKeyClient)


## Architecture

![架构图](http://img.gitnavi.com/tkey/tkey-sso-architecture.jpg)

- 上图的视频讲解：[B 站](https://www.bilibili.com/video/av65883281/)、[腾讯视频](https://v.qq.com/x/page/e0920wdqe7v.html)
- OAuth2.0 授权码模式细节时序图可以查看：[点击我查看](http://img.gitnavi.com/tkey/tkey-oauth.png)

## Preview（Gif）

- 主图需要右键复制地址，然后粘贴到地址栏才能打开 Orz..
- **登录完整过程：** [主图](https://upload-images.jianshu.io/upload_images/19119711-cd483cefb50eb763.gif)、[备图](http://img.gitnavi.com/tkey/tkey-sso-login.gif)
- **Grafana 监控大屏：** [主图](https://upload-images.jianshu.io/upload_images/19119711-af9b3d3411db1da1.gif)、[备图](http://img.gitnavi.com/tkey/actuator-prometheus-grafana.gif)
- **GoAccess 监控大屏：** [主图](https://upload-images.jianshu.io/upload_images/19119711-b3bcc4edcf0df007.gif)、[备图](http://img.gitnavi.com/tkey/goaccess-data.gif)
- **Postman 接口调用：** [主图](https://upload-images.jianshu.io/upload_images/19119711-a8316b794bf4bf56.gif)、[备图](http://img.gitnavi.com/tkey/postman-request-api.gif)
- **Docker 容器管理：** [主图](https://upload-images.jianshu.io/upload_images/19119711-281dd6b40f2d7fc7.gif)、[备图](http://img.gitnavi.com/tkey/portainer-docker.gif)
- **Jenkins 部署流水线：** [主图](https://upload-images.jianshu.io/upload_images/19119711-2d20e2fba98ddbbd.gif)、[备图](http://img.gitnavi.com/tkey/tkey-jenkins.gif)
- **JProfiler 压测变化：** [主图](https://upload-images.jianshu.io/upload_images/19119711-922b8202de206b06.gif)、[备图](http://img.gitnavi.com/tkey/tkey-jprofiler.gif)
- **VisualVM 压测变化：** [主图](https://upload-images.jianshu.io/upload_images/19119711-067bcdf1a6e95b44.gif)、[备图](http://img.gitnavi.com/tkey/tkey-visualvm.gif)


## Quick Start

- 单元测试：[主图](https://upload-images.jianshu.io/upload_images/19119711-6bc18bb5b1063911.gif)、[备图](http://img.gitnavi.com/tkey/tkey-junit-test.gif)
- TKey SSO Server JAR 方式部署过程：[主图](https://upload-images.jianshu.io/upload_images/19119711-72e375355e3df651.gif)、[备图](http://img.gitnavi.com/tkey/tkey-runapp-jar.gif)
- TKey SSO Server Docker Compose：[主图](https://upload-images.jianshu.io/upload_images/19119711-10011adf8a15e049.gif)、[备图](http://img.gitnavi.com/tkey/tkey-sso-server-docker-compose.gif)
- TKey SSO Client Management Docker Compose：[主图](https://upload-images.jianshu.io/upload_images/19119711-8edd4a914ed4540a.gif)、[备图](http://img.gitnavi.com/tkey/tkey-sso-client-management-docker-compose.gif)
- 项目完全依赖 Lombok（推荐），如果没有用过可以参考 [该篇文章](https://github.com/cdk8s/cdk8s-team-style/blob/master/dev/backend/java/java-lombok.md)
- 项目最优搭配 IntelliJ IDEA，如果还没用过可以参考 [该系列文章（我们的作品）](https://github.com/judasn/IntelliJ-IDEA-Tutorial)
- Maven 中央仓库已经申请下来，大家现在可以直接使用我们自己封装的 REST API 客户端了

## Documentation

- 我们统一了 TKey 项目的所有文档，方便大家查看
    - Github：<https://github.com/cdk8s/tkey-docs>
    - Gitee：<https://gitee.com/cdk8s/tkey-docs>
    - Gitbook：<https://160668873.gitbook.io/tkey-docs/>
- **认识阶段 （必读）**
    - 单点登录系统认知与基础介绍：[Github](https://github.com/cdk8s/tkey-docs/blob/master/other/tkey-baisc.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/other/tkey-baisc.md)
    - 故意设计点（常见问题）：[Github](https://github.com/cdk8s/tkey-docs/blob/master/faq/README.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/faq/README.md)
    - 项目结构与端口占用：[Github](https://github.com/cdk8s/tkey-docs/blob/master/other/project-structure.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/other/project-structure.md)
    - OAuth2.0 四种模式：[Github](https://github.com/cdk8s/tkey-docs/blob/master/server/oauth-grant-type/README.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/server/oauth-grant-type/README.md)
    - JAR 方式部署：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/jar-runapp.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/jar-runapp.md)
    - Docker 方式部署：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/docker-runapp.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/docker-runapp.md)
    - Docker Compose 方式部署：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/docker-compose-runapp.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/docker-compose-runapp.md)
- TKey Server 开发阶段
    - 开发改造引导：[Github](https://github.com/cdk8s/tkey-docs/blob/master/server/dev.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/server/dev.md)
- TKey Management 开发阶段（也是前后端分离的最佳实践示例）
    - 后端开发改造引导：[Github](https://github.com/cdk8s/tkey-docs/blob/master/management/dev-backend.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/management/dev-backend.md)
    - 前端开发改造引导：[Github](https://github.com/cdk8s/tkey-docs/blob/master/management/dev-frontend.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/management/dev-frontend.md)
- TKey Client Java 开发阶段
    - 自己封装的 REST Client：[Github](https://github.com/cdk8s/tkey-docs/blob/master/client/dev-rest-client.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/client/dev-rest-client.md)
    - Spring Security 支持：[Github](https://github.com/cdk8s/tkey-docs/blob/master/client/dev-spring-security-client.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/client/dev-spring-security-client.md)
- 测试阶段
    - 单元测试：[Github](https://github.com/cdk8s/tkey/blob/master/src/test/java/com/cdk8s/tkey/server/controller/AuthorizationCodeByFormTest.java)、[Gitee](https://gitee.com/cdk8s/tkey/blob/master/src/test/java/com/cdk8s/tkey/server/controller/AuthorizationCodeByFormTest.java)
    - 压力测试：[Github](https://github.com/cdk8s/tkey-docs/blob/master/test/performance.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/test/performance.md)
- 部署阶段
    - 生产注意事项：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/production-environment.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/production-environment.md)
    - 部署环境搭建：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/deployment-core.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/deployment-core.md)
- 监控阶段
    - Spring Boot Micrometer：[Github](https://github.com/cdk8s/tkey-docs/blob/master/deployment/micrometer.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/deployment/micrometer.md)
    - 其他工具全在 `部署环境搭建`，请自行查看
- 线上问题诊断
    - [Actuator 在线修改 log 输出级别（Gif 动图）](http://img.gitnavi.com/tkey/actuator-update-log-level.gif)
    - [Arthas 诊断 Docker 应用](https://alibaba.github.io/arthas/docker.html#dockerjava)
    - 夜间开放端口，挑选流量远程 Debug：[Github](https://github.com/cdk8s/tkey-docs/blob/master/server/remote-debug.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/server/remote-debug.md)


## TKey Client

- Java 前后端分离最佳实践
    - TKey SSO Client Management Backend：[Github](https://github.com/cdk8s/tkey-management)、[Gitee](https://gitee.com/cdk8s/tkey-management)
    - TKey SSO Client Management Frontend：[Github](https://github.com/cdk8s/tkey-management-frontend)、[Gitee](https://gitee.com/cdk8s/tkey-management)
    - Angular、Vue 的前后端分离版本会在稍后几周发出来
- Java REST API 客户端：[Github](https://github.com/cdk8s/tkey-client-java)、[Gitee](https://gitee.com/cdk8s/tkey-client-java)
- Java Spring Security 客户端：[Github](https://github.com/cdk8s/tkey-client-java-spring-security)、[Gitee](https://gitee.com/cdk8s/tkey-client-java-spring-security)
- C#（暂缺）
- GO（暂缺）
- PHP（暂缺）
- Python（暂缺）
- Ruby（暂缺）
- Node.js（暂缺）

## Share

- Grafana Dashboard：[Github](https://github.com/cdk8s/tkey-docs/blob/master/share-file/grafana/dashboard.json)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/share-file/grafana/dashboard.json)
- Postman API：[Github](https://github.com/cdk8s/tkey-docs/blob/master/share-file/postman/tkey-sso-server-api_collection_2.1_format.json)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/share-file/postman/tkey-sso-server-api_collection_2.1_format.json)
- Run JAR Shell：[Github](https://github.com/cdk8s/tkey-docs/blob/master/share-file/shell/runapp.sh)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/share-file/shell/runapp.sh)


## Roadmap

- 规划版本：[Github](https://github.com/cdk8s/tkey-docs/blob/master/roadmap/README.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/roadmap/README.md)

## Changelog

- 版本更新：[Github](https://github.com/cdk8s/tkey-docs/blob/master/changelog/README.md)、[Gitee](https://gitee.com/cdk8s/tkey-docs/blob/master/changelog/README.md)


## Issues

- 目前只开放了一个 issues 入口，集中问题，可以方便大家检索。
- 去提问：[Github](https://github.com/cdk8s/tkey-issues)、[Gitee](https://gitee.com/cdk8s/tkey-issues)

## Contributors

- 暂无
- 欢迎 pull request

## Adopters

- 去申请：[Github](https://github.com/cdk8s/tkey-issues/issues/1)、[Gitee](https://gitee.com/cdk8s/tkey-issues/issues/1)
- 以企业角色联系我进行咨询有优先权，我们会花更多耐心进行讲解和帮助
- 所以，请在加好友之后先表明公司、立场


## Sponsors

- 暂无

## Backer

- [我要喝喜茶 Orz..](http://www.youmeek.com/donate/)


## Join

- 邮箱：`cdk8s#qq.com`
- 博客：<https://cdk8s.github.io/>
- Github：<https://github.com/cdk8s>
- Gitee：<https://gitee.com/cdk8s>
- 公众号

![公众号](http://img.gitnavi.com/markdown/cdk8s_qr_300px.png)


## Jobs

- 我们在广州
- 有广州或深圳的合作、Offer 欢迎联系我们
- 邮箱：`cdk8s#qq.com`
- 公众号：`联系我们`

## Thanks

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [CAS](https://github.com/apereo/cas)
- [Okta](https://www.okta.com/)


## Copyright And License

- Copyright (c) CDK8S. All rights reserved.
- Licensed under the **MIT** license.
- **再次强调： 因为是 MIT 协议，大家有不满意的，除了 PR 也可以 fork 后自己尽情改造!**


