<#import "./common.ftlh" as c>

<@c.page>
    <@c.show_post c.findBy(posts, "id", post_id) users false false/>
</@c.page>
