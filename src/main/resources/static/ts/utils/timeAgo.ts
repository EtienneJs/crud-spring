export function timeago(){
    const timeElements = document.querySelectorAll('.timeago') as NodeListOf<HTMLTimeElement>;  
    timeElements.forEach(el => {
        const datetime:string = el.dateTime;
        const date: Date = new Date(datetime);
        const now:Date = new Date();
        const diffMs:number = (now.getTime() - date.getTime());
        const diffSec:number = Math.floor(diffMs / 1000);
        const diffMin:number = Math.floor(diffSec / 60);
        const diffHr:number = Math.floor(diffMin / 60);
        const diffDay:number = Math.floor(diffHr / 24);

        let text:string = '';
        if (diffDay > 0) {
        text = `hace ${diffDay} día${diffDay > 1 ? 's' : ''}`;
        } else if (diffHr > 0) {
        text = `hace ${diffHr} hora${diffHr > 1 ? 's' : ''}`;
        } else if (diffMin > 0) {
        text = `hace ${diffMin} minuto${diffMin > 1 ? 's' : ''}`;
        } else {
            text = `hace unos segundos`;
        }
        console.log('AAA');
        el.textContent = text;
    });
}