import heartFill from "../../../../assets/heart-fill.png";
import heartBlank from "../../../../assets/heart-blank.png";

interface HealthPointsProps {
    currentHealth: number;
    maxHealth: number;
}

function HealthPoints({currentHealth, maxHealth}: HealthPointsProps) {
    const hearts = Array.from({length: currentHealth}, (_, index) => (
        <img key={`heart-${index}`} src={heartFill} alt="filled heart" className="heart"/>
    ));
    const blanks = Array.from({length: maxHealth - currentHealth}, (_, index) => (
        <img key={`blank-${index}`} src={heartBlank} alt="empty heart" className="heart"/>
    ));

    return (
        <div className="hearts-bar non-clickable row-container">
            {[...hearts, ...blanks]}
        </div>
    );
}

export default HealthPoints;
