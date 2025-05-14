import { useState } from 'react';
import "../styles/sing.css";
import {signUp} from "../api/login.service.ts";


interface SignUpProps {
    close: () => void;
    onSignUpSuccess: (username: string) => void;
}

const SignUp: React.FC<SignUpProps> = ({ close, onSignUpSuccess }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await signUp(username, password);
            onSignUpSuccess(username);
            close();
        } catch (error) {
            console.error("Sign up failed:", error);
        }
    };

    return (
        <div className="sing-container">
            <h1 className={"title"}>Sign Up</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Sign Up</button>
            </form>
        </div>
    );
};

export default SignUp;