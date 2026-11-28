<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=post/>

    <section class="comments">
        <h2>Comments</h2>
        <#list post.comments as comment>
            <article>
                <div class="information">${comment.user.login}, ${comment.creationTime?datetime}</div>
                <div class="body">${comment.text}</div>
            </article>
        <#else>
            <p>No comments yet</p>
        </#list>
    </section>

    <#if user??>
        <div class="form">
            <div class="header">Add Comment</div>
            <div class="body">
                <form method="post" action="<@spring.url ('/post/' + post.id?c)/>">
                    <div class="field">
                        <div class="name"><label for="comment-text">Comment</label></div>
                        <div class="value">
                            <textarea id="comment-text" name="text" rows="5" maxlength="65000">${commentForm.text!}</textarea>
                        </div>
                        <@c.error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Send">
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@c.page>
