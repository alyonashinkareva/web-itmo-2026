<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?? && post_id?is_number>
        <#assign post = c.findBy(posts, "id", post_id)!/>
    </#if>


    <#if post?has_content>
        <@c.post currentPost=post isFull=true/>
    <#else>
        No such post
    </#if>
</@c.page>