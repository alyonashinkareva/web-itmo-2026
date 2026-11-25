import './App.css';
import Enter from "./components/Middle/Main/Enter/Enter";
import WritePost from "./components/Middle/Main/WritePost/WritePost";
import Index from "./components/Middle/Main/Index/Index";
import React, {useCallback, useState} from "react";
import Middle from "./components/Middle/Middle";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";

function App({users, postsData}) {

    const [user, setUser] = useState(null)
    const [page, setPage] = useState('index')
    const [posts, setPosts] = useState(postsData)

    const createPost = useCallback((post) => {
        setPosts((posts) => {
            const maxId = Math.max(...posts.map((post) => post.id)) + 1
            return [...posts, {...post, id: maxId}]
        })
    }, [])

    const getPage = useCallback((page) => {
        switch (page) {
            case 'index':
                return (<Index/>)
            case 'enter':
                return (<Enter users={users} setUser={setUser} setPage={setPage}/>)
            case 'writePost':
                return (<WritePost createPost={createPost} setPage={setPage}/>)
        }
    }, [])

    return (
        <div className="App">
            <Header setUser={setUser} setPage={setPage} user={user}/>
            <Middle
                posts={posts}
                page={getPage(page)}
            />
            <Footer/>
        </div>
    );
}

export default App;
