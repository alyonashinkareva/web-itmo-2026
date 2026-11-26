import React from 'react';

const EnterOrRegister = ({user, setUser, setPage}) => {

    return (
        <div className="enter-or-register-box">
            {user
                ?
                <>
                    {user.login}
                    <a href="#" onClick={(event) => {
                        setUser(null)
                        event.preventDefault()
                    }}>
                        Logout
                    </a>
                </>
                :
                <>
                    <a href="" onClick={(event) => {
                        setPage('enter')
                        event.preventDefault()
                    }}>Enter</a>
                    <a href="">Register</a>
                </>
            }
        </div>
    );
};

export default EnterOrRegister;