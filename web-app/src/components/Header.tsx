import { useState } from "react";
import "../styles/header.css";

interface HeaderProps {
    player: string;
    login: () => void;
    singIn: () => void;
}

function Header({ player, login, singIn }: HeaderProps) {
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => setMenuOpen((prev) => !prev);

    return (
        <>
            <div className={`sidebar ${menuOpen ? "open" : ""}`}>
                <button className="close-btn" onClick={toggleMenu}>×</button>
                <h2 className="sidebar-title">Games</h2>
                <button className="sidebar-item">Catshot Roulette</button>
            </div>

            <div className="header-container">
                <div className="header-left">
                    <img
                        src="/game-icon.svg"
                        alt="Menu"
                        className="menu-icon"
                        onClick={toggleMenu}
                    />
                    <span className="header-title">GameStudio</span>
                </div>

                <div className="header-controls">
                    {player != "GUEST" && <span className="header-player">{player}</span>}
                    <div className={"header-buttons"}>
                        <button onClick={login}>Log In</button>
                        <button onClick={singIn}>Sign Up</button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Header;