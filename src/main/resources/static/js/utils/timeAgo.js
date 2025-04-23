export function timeago() {
    const timeElements = document.querySelectorAll('.timeago');
    timeElements.forEach(el => {
        console.log(el);
        const datetime = el.dateTime;
        const date = new Date(datetime);
        const now = new Date();
        const diffMs = (now.getTime() - date.getTime());
        const diffSec = Math.floor(diffMs / 1000);
        const diffMin = Math.floor(diffSec / 60);
        const diffHr = Math.floor(diffMin / 60);
        const diffDay = Math.floor(diffHr / 24);
        let text = '';
        if (diffDay > 0) {
            text = `hace ${diffDay} día${diffDay > 1 ? 's' : ''}`;
        }
        else if (diffHr > 0) {
            text = `hace ${diffHr} hora${diffHr > 1 ? 's' : ''}`;
        }
        else if (diffMin > 0) {
            text = `hace ${diffMin} minuto${diffMin > 1 ? 's' : ''}`;
        }
        else {
            console.log(diffDay);
            text = `hace unos segundos`;
        }
        el.textContent = text;
    });
}
