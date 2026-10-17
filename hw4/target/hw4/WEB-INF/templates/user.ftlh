<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="currUser" type="ru.itmo.web.hw4.model.User" -->
<#-- @ftlvariable name="nextUser" type="ru.itmo.web.hw4.model.User" -->
<#-- @ftlvariable name="prevUser" type="ru.itmo.web.hw4.model.User" -->
<#-- @ftlvariable name="postCnt" type="ru.itmo.web.hw4.model.Post" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#if currUser??>
        <div class = "user-info">
            <div>
                <h2> <@c.userlink currUser/> </h2>
                <ul>
                    <li> id: ${currUser.id} </li>
                    <li> handle: ${currUser.handle} </li>
                    <li> name: ${currUser.name} </li>
                    <li>
                        postsCount: ${postCnt}
                        <a href="/posts?user_id=${currUser.id}">View</a>
                    </li>
                </ul>
            </div>
            <img src="/img/avatar.png" alt="Avatar">
        </div>
        <div class="user-nav">
            <#if prevUser??>
                <a href="/user?handle=${prevUser.handle}">&larr;</a>
            <#else>
                <span class="unwork">←</span>
            </#if>

            <#if nextUser??>
                <a href="/user?handle=${nextUser.handle}">&rarr;</a>
            <#else>
                <span class="unwork">→</span>
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
