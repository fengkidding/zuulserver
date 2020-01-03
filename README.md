# zuulserver
网关

网关：ZuulserverApplication含有@EnableZuulProxy标识为zuul网关。

文件上传接口：FileController。

jwt认证鉴权：AuthFilter网关统一验证，成功后将鉴权信息和日志唯一标识保存至请求头。

### 目录介绍：

filter：过滤器

common：通用的功能模块 log：统一结构化日志工具类 util：工具类

config：配置类

controller：api接口

model：数据对象

pattern：设计模式目录