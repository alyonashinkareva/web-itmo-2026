<#import "./common.ftlh" as c>

<@c.page>
    <#assign p = c.findBy(posts, "id", post_id)!""/>
    <@c.show_post p users false false/>
</@c.page>
