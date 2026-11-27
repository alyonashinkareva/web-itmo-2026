<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?? && post_id?is_number>
        <#assign selectedPost = c.findBy(posts, "id", post_id)!/>
    <#else>
        <#assign selectedPost = ""/>
    </#if>

    <#if selectedPost?has_content>
        <@c.post post=selectedPost short=false/>
    <#else>
        <p>No such post</p>
    </#if>
</@c.page>