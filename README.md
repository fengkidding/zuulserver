# zuulserver
网关

网关：ZuulserverApplication含有@EnableZuulProxy标识为zuul网关。

文件上传接口：FileController。

jwt认证鉴权：AuthFilter网关统一验证，成功后将鉴权信息和日志唯一标识保存至请求头。
