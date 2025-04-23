import { formFieldsType } from "../types.js";

export function formFields <T extends ReadonlyArray<string>>(form:HTMLElement,inputName:T):Record<T[number],formFieldsType>{
    return Object.fromEntries(
        inputName.map( (name:string) =>[
            name,
            form.querySelector(`input[name='${name}'], select[name='${name}'],textarea[name='${name}']`) as formFieldsType
            ]
        )
    ) as Record<typeof inputName[number],formFieldsType>
}