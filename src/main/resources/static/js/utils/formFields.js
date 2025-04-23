export function formFields(form, inputName) {
    return Object.fromEntries(inputName.map((name) => [
        name,
        form.querySelector(`input[name='${name}'], select[name='${name}'],textarea[name='${name}']`)
    ]));
}
