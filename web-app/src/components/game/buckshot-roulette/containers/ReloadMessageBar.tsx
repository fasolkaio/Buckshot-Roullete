import bullet from '../../../../assets/bullet-blank.svg';
import '../../../../styles/containers.css'

interface ReloadMessageProps {
    blanks: number;
    lives: number;
}

function ReloadMessageBar({ blanks, lives }: ReloadMessageProps) {
    const totalBullets = blanks + lives;

    const bullets = Array.from({ length: totalBullets }, (_, index) => {
        const isLive = index < lives;
        const bulletClass = isLive ? 'live' : '';

        return (
            <img
                key={index}
                src={bullet}
                alt={isLive ? "Live bullet" : "Blank bullet"}
                className={`bullet ${bulletClass}`}
            />
        );
    });

    return (
        <div className="message-container">
            <h2>Gun was reloaded</h2>
            <div className="bullet-line">
                {bullets}
            </div>
        </div>
    );
}

export default ReloadMessageBar;