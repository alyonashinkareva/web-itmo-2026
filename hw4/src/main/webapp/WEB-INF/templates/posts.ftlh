<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?? && user_id?is_number>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <#if u?has_content>
        <#list posts?reverse as post>
            <#if post.user_id == user_id>
                <@c.post currentPost=post isFull=false/>
            </#if>
        </#list>
    <#else>
        No such user
    </#if>
</@c.page>