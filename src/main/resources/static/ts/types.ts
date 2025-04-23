
export type toast_config_type = {
    text ?:string |undefined,
    duration ?: number|undefined,
    destination ?: string|undefined,
    newWindow ?:boolean|undefined,
    close ?:boolean|undefined,
    gravity ?: toastDirectionGravity_enum,
    position ?: toastPosition_enum,
    stopOnFocus ?: boolean|undefined,
    style ?:{background:string},
    onClick ?: ()=>any
}

export enum toastDirectionGravity_enum {
    TOP = "top",
    BOTTOM = "bottom"
}
export enum toastPosition_enum {
    RIGHT = "right",
    CENTER = "center",
    LEFT = "left" 
}


export type toastStyle_enum = {
    success : {background:string},
    error : {background:string}
}

export type StateEnum ="NO_COMPLETED"|"COMPLETED"|"IN_PROGRESS"|"ALL"|"BACKLOG"

export interface dropdownMenuBody_interface {
    dropdownMenuBtnProps:dropdownMenuBtnProps_type,
    labelledby:string,
    optionsProps:optionsProps_type[]
}
export type optionsProps_type<T = unknown> = {
    onclickFunction: T
    ,title:string
} 

export type dropdownMenuBtnProps_type = {
    moreStyle:string,
    title:string
}

export type formFieldsType = HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement;

export interface Response<T = unknown> {
    message:string,
    data: T,
    timestamp:string
}

export type TodoType = {
    id:string,
    title:string, 
    description:string,
    createdAt:string,
    endDate:string,
    state:StateEnum
}


export type form = "title" | "description" | "state" | "endDate";
export type formAdd = "title" | "description" | "endDate";