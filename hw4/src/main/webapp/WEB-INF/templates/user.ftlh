<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id?? && user_id?is_number>
        <#assign u = c.findBy(users, "id", user_id)!/>
    <#else>
        <#assign u = ""/>
    </#if>



    <#if u?has_content>
        <#assign postCount = 0/>
        <#list posts as post>
            <#if post.user_id == u.id>
                <#assign postCount = postCount + 1/>
            </#if>
        </#list>
        <div class="profile">
            <img class="avatar" src="/img/avatar.png" alt="User avatar">

            <h1><@c.userlink u/></h1>

            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li>posts: <a href="/posts?user_id=${u.id}">${postCount}</a></li>
            </ul>
        </div>
        <#list users as current>
            <#if current.id == u.id>
                <div>
                    <#if current?index gt 0>
                        <a href="/user?handle=${users[current?index - 1].handle}">←</a>
                    <#else>
                        <span style="color: silver;">←</span>
                    </#if>

                    <#if current?index lt users?size - 1>
                        <a href="/user?handle=${users[current?index + 1].handle}">→</a>
                    <#else>
                        <span style="color: silver;">→</span>
                    </#if>
                </div>
            </#if>
        </#list>
    <#else>
        No such user
    </#if>
</@c.page>
