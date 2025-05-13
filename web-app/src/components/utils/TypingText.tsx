import {useEffect, useState} from "react";

interface TypingTextProps {
    text?: string;
    speed?: number;
}

const TypingText = ({text = "", speed = 70}: TypingTextProps) => {
    const [displayedText, setDisplayedText] = useState("");

    useEffect(() => {
        let cancelled = false;

        const typeLetter = async () => {
            setDisplayedText("");
            for (let i = 0; i < text.length; i++) {
                if (cancelled) return;
                await new Promise((resolve) => setTimeout(resolve, speed));
                setDisplayedText((prev) => prev + text[i]);
            }
        };

        typeLetter();

        return () => {
            cancelled = true;
        };
    }, [text, speed]);

    return (
        <span>
      {displayedText}
            <span className="blinking-cursor">|</span>
    </span>
    );
};

export default TypingText;
