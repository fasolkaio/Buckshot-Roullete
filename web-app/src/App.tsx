import './styles/App.css';
import './styles/services.css';
import MenuBuckshotRoulette from "./components/game/buckshot-roulette/containers/MenuBuckshotRoulette.tsx";
import TopScore from "./components/services/TopScore.tsx";
import Rating from "./components/services/Rating.tsx";
import Comment from "./components/services/Comment.tsx";
import Header from "./components/Header.tsx";
import SignIn from "./components/SingIn.tsx";
import SignUp from "./components/SingUp.tsx";
import { useState } from "react";

function App() {
    const [loggedPlayer, setLoggedPlayer] = useState<string>('GUEST');
    const [isSignIn, setIsSignIn] = useState<boolean>(false);
    const [isSignUp, setIsSignUp] = useState<boolean>(false);

    const login = () => {
        setIsSignIn(true);
        setIsSignUp(false);
    };

    const signUp = () => {
        setIsSignUp(true);
        setIsSignIn(false);
    };

    const handleCloseModal = () => {
        setIsSignIn(false);
        setIsSignUp(false);
    };

    const handleSignUpSuccess = (username: string) => {
        setLoggedPlayer(username);
    };

    const handleLoginSuccess = (username: string) => {
        setLoggedPlayer(username);
    };

    return (
        <>
            <Header player={loggedPlayer} login={login} singIn={signUp} />

            {isSignIn && <SignIn close={handleCloseModal} onLoginSuccess={handleLoginSuccess} />}
            {isSignUp && <SignUp close={handleCloseModal} onSignUpSuccess={handleSignUpSuccess} />}

            {!isSignIn && !isSignUp && (
                <div className={"content"}>
                    <MenuBuckshotRoulette player={loggedPlayer} />
                    <div className={"service-container"}>
                        <div className={"rating-score-container"}>
                            <Rating player={loggedPlayer} game={'buckshot-roulette'} />
                            <TopScore player={loggedPlayer} game={'buckshot-roulette'} />
                        </div>
                        <Comment player={loggedPlayer} game={'buckshot-roulette'}></Comment>
                    </div>
                </div>
            )}
        </>
    );
}

export default App;
