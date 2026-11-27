<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?? && user_id?is_number>
        <#assign author = c.findBy(users, "id", user_id)!/>
    <#else>
        <#assign author = ""/>
    </#if>

    <#if author?has_content>
        <h1>Posts by <@c.userlink author/></h1>

        <#assign found = false/>

        <#list posts?reverse as post>
            <#if post.user_id == author.id>
                <@c.post post=post/>
                <#assign found = true/>
            </#if>
        </#list>

        <#if !found>
            <p>No posts yet</p>
        </#if>
    <#else>
        <p>No such user</p>
    </#if>
</@c.page>