import {useRef, useState, useEffect} from 'react';
import '../../styles/animations.css';

interface SpriteProps {
    sprite: string;
    widthFrame: number;
    heightFrame: number;
    frameCount: number;
    animationDuration: number;
    scaleToParent?: boolean;
    className?: string;
}

function Sprite({
                    sprite,
                    widthFrame,
                    heightFrame,
                    frameCount,
                    animationDuration,
                    scaleToParent = false,
                    className
                }: SpriteProps) {
    const animationNameRef = useRef(`sprite-animation-${Math.random().toString(36).slice(2, 9)}`);
    const animationName = animationNameRef.current;

    const containerRef = useRef<HTMLDivElement>(null);
    const [scaleX, setScaleX] = useState(1);
    const [scaleY, setScaleY] = useState(1);

    useEffect(() => {
        if (!scaleToParent) return;

        const updateScale = () => {
            if (!containerRef.current) return;

            const {width, height} = containerRef.current.getBoundingClientRect();
            setScaleX(width / widthFrame);
            setScaleY(height / heightFrame);
        };

        updateScale();
        const resizeObserver = new ResizeObserver(updateScale);
        if (containerRef.current) {
            resizeObserver.observe(containerRef.current);
        }

        return () => {
            if (containerRef.current) {
                resizeObserver.unobserve(containerRef.current);
            }
        };
    }, [scaleToParent, widthFrame, heightFrame]);

    const dynamicStyle = `
        @keyframes ${animationName} {
            from {
                background-position: 0 0;
            }
            to {
                background-position: -${widthFrame * (frameCount - 1)}px 0;
            }
        }
    `;

    return (
        <>
            <style>{dynamicStyle}</style>
            <div
                ref={containerRef}
                className={`animation-container ${className || ''}`}
                style={{
                    width: scaleToParent ? '100%' : `${widthFrame}px`,
                    height: scaleToParent ? '100%' : `${heightFrame}px`,
                    position: 'relative',
                    overflow: 'hidden',
                }}
            >
                <div
                    style={{
                        width: `${widthFrame}px`,
                        height: `${heightFrame}px`,
                        backgroundImage: `url(${sprite})`,
                        backgroundRepeat: 'no-repeat',
                        animation: `${animationName} ${animationDuration}ms steps(${frameCount - 1}, end) forwards`,
                        transformOrigin: 'top left',
                        transform: `scale(${scaleX}, ${scaleY})`,
                        position: 'absolute',
                        top: 0,
                        left: 0,
                    }}
                />
            </div>
        </>
    );
}

export default Sprite;
