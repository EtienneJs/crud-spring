export function dropdownMenu_body({ dropdownMenuBtnProps, labelledby, optionsProps }) {
    let options = "";
    optionsProps.forEach(({ onclickFunction, title }) => {
        options += dropdownMenu_options({ onclickFunction, title });
    });
    return `
    ${dropdownMenu_btn(dropdownMenuBtnProps, labelledby)}
    <ul class="dropdown-menu" aria-labelledby="${labelledby}">
        ${options}
    </ul>`;
}
export function dropdownMenu_btn({ moreStyle }, labelledby) {
    return `<button class="btn dropdown-toggle ${moreStyle}" type="button" id="${labelledby}" data-bs-toggle="dropdown" aria-expanded="false"></button>`;
}
export function dropdownMenu_options({ onclickFunction, title }) {
    return `<li><button class="dropdown-item" onclick="${onclickFunction}" type="button">${title}</button></li>`;
}
