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

<#-- @ftlvariable name="currPage" type="ru.itmo.web.hw4.model.User" -->
<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
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
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}" <#if currPage == item.href>
                            class="active"
                                </#if>>
                            ${item.name}
                        </a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as post>
            <section>
                <div class="header">Post #${post.id}</div>
                <div class="body">
                    ${post.clippedText}
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <a href="/user?handle=${user.handle}" class="user-link">
        ${user.name}
        <#if !nameOnly>
            <span class="${user.color}"> ${user.handle} </span>
        </#if>
    </a>
</#macro>

<#macro handlelink user>
    <a href="/user?handle=${user.handle}">${user.handle}</a>
</#macro>

<#macro post p fulltext = false>
    <article>
        <div class="title">${p.title}</div>
        <div class="information">
            <#assign found = false>
            By
            <#list users as u>
                <#if u.id == p.user_id>
                    <@userlink u/>
                    <#assign found = true>
                    <#break>
                </#if>
            </#list>
        <#if !found>
            ${p.user_id?c}
        </#if>
        </div>
        <div class="body">
            <p>
                <#if !fulltext>
                    ${p.clippedText}
                    <#if p.text?length gt 350>
                        <a href="post?post_id=${p.id}">View all</a>
                    </#if>
                <#else>
                    ${p.text}
                </#if>
            </p>
        </div>
    </article>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

