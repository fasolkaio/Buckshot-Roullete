import { useState } from 'react';
import "../styles/sing.css";
import {login} from "../api/login.service.ts";

interface SignInProps {
    close: () => void;
    onLoginSuccess: (username: string) => void;
}

const SignIn: React.FC<SignInProps> = ({ close, onLoginSuccess }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const token = await login(username, password);
            console.log("Logged in with token:", token);
            onLoginSuccess(username);
            close();
        } catch (error) {
            console.error("Login failed:", error);
        }
    };

    return (
        <div className="sing-container">
            <h1 className={"title"}>Log In</h1>
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
                <button type="submit">Log In</button>
            </form>
        </div>
    );
};

export default SignIn;
