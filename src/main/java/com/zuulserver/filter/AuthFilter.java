package com.zuulserver.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zuulserver.common.util.LogBackUtils;
import com.zuulserver.common.util.SessionUtils;
import com.zuulserver.common.util.SignUtils;
import com.zuulserver.model.constant.MemberConstant;
import com.zuulserver.model.enums.ResultEnum;
import com.zuulserver.pattern.factory.ResultVOFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 前置过滤器
 *
 * @author f
 * @date 2019-11-08
 */
@Component
public class AuthFilter extends ZuulFilter {

    /**
     * 前置过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * jwt认证鉴权
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = SessionUtils.getToken(request);
        LogBackUtils.info("AuthInterceptor.preHandle token=" + token);
        if (StringUtils.isNotBlank(token)) {
            try {
                DecodedJWT decodedJWT = SignUtils.verifyToken(token, SignUtils.signingSecret);
                Integer memberId = decodedJWT.getClaim(MemberConstant.MEMBER_ID).asInt();
                String userName = decodedJWT.getClaim(MemberConstant.USER_NAME).asString();
                LogBackUtils.info("AuthInterceptor.preHandle id=" + memberId + ",userName=" + userName);
                ctx.addZuulRequestHeader(MemberConstant.MEMBER_ID, memberId.toString());
            } catch (Exception e) {
                LogBackUtils.error("AuthInterceptor.preHandle token异常:token=" + token, e);
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(JSON.toJSONString(ResultVOFactory.getResult(ResultEnum.NO_TOKEN)));
                ctx.getResponse().setCharacterEncoding("UTF-8");
                ctx.getResponse().setContentType("application/json;cahrset=UTF-8");
            }
        } else {
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(JSON.toJSONString(ResultVOFactory.getResult(ResultEnum.NO_TOKEN)));
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("application/json;cahrset=UTF-8");
        }
        return null;
    }
}
