<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a <@logged_href ""/> class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a <@logged_href ""/>><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a <@logged_href ""/>><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if logged_user??>
                <@userlink logged_user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}]/>
            <ul>
                <#list items as item>
                    <li>
                        <a <#if item.href == uri>
                        class="active"
                        </#if><@logged_href "${item.href}"/>>${item.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
       <@show_aside_posts posts/>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#function hrefuser user>
    <#return "/user?handle=${user.handle}"/>
</#function>

<#macro userlink user nameOnly=false>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    
    <a <@logged_href hrefuser(user)/> <#if !nameOnly>class="userlink" style="color: ${user.color?lower_case};"</#if>><#if nameOnly>${user.name}<#else>${user.handle}</#if></a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNextBy items key value>
    <#assign saw = false/>
    <#list items as item>
        <#if saw>
            <#return item/>
        </#if>
        <#if item[key] == value>
            <#assign saw = true/>
        </#if>
    </#list>
</#function>

<#function findPrevBy items key value>
    <#assign u = ""/>
    <#list items as item>
        <#if item[key] == value>
            <#return u/>
        </#if>
        <#assign u = item/>
    </#list>
</#function>

<#macro arrow content user>
    <div class="arrow">
        <a <#if user?has_content>class="active" <@logged_href hrefuser(user)/><#else>class="disable"</#if>>
            ${content}
        </a>
    </div>
</#macro>

<#macro to_short text length active>
    <#if active && text?length gt length>
                    ${text?substring(0, length)}...
                <#else>
                    ${text}
                </#if>
</#macro>

<#macro show_post post users is_short=false is_short_title=false>
    <article>
            <div class="title"><@to_short post.title 100 is_short_title/></div>
            <div class="information">By <@userlink findBy(users "id" post.user_id)/></div>
            <div class="body">
                <@to_short post.text 350 is_short/>
            </div>
        </article>
</#macro>

<#macro show_posts posts users>
    <#list posts?reverse as post>
    <@show_post post, users true false/>
    </#list>
</#macro>

<#macro show_aside_post post>
    <section>
            <div class="header"><@to_short post.title 100 true/></div>
            <div class="body"><@to_short post.text 350 true/></div>
            <div class="footer">
                <a <@logged_href "/posts?post_id=${post.id}"/>>View all</a>
            </div>
        </section>
</#macro>

<#macro show_aside_posts posts>
    <#list posts?reverse as post>
    <@show_aside_post post/>
    </#list>
</#macro>

<#macro table_users users>
    <div class="datatable">
            <div class="caption">Users</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Handle</th>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>
                    <#list users as user>
                        <tr>
                            <td>${user.id}</td>
                            <td><@userlink user/></td>
                            <td>${user.name}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
</#macro>

<#macro logged_href uri>
    href="${uri}<#if logged_user??><#if uri?contains("?")>&amp;<#else>?</#if>logged_user_id=${logged_user.id}</#if>"
</#macro>