import { dropdownMenu_body } from "./componentes.js";
import { CONFIG, inputName, inputNameAdd, TOAST_STYLES } from "./constantes.js";
import { TodoType, Response,StateEnum, optionsProps_type, dropdownMenuBtnProps_type, formFieldsType, form, formAdd } from "./types";
import {  formFields,timeago,toastify,validationsInputs} from "./utils/index.js";
declare const bootstrap: any;
const myModal = new bootstrap.Modal(document.getElementById("exampleModal"))
const myModalAdd = new bootstrap.Modal(document.getElementById("addModal"))


function clearInputsForms <T extends Record<string,formFieldsType>>(inputs:T){
    Object.values(inputs).forEach((field)=>{field.value = ""});
}

// Función para activar el modo de edición
function openModalEdit(id:string) {
    fetch(`/todos/${id}`,{
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    })
    .then(response => response.json())
    .then((response:Response<TodoType>) => {
        const {id,description,state,title,endDate} = response.data
        const form:HTMLElement = document.getElementById("form_modal_editTodo")!;     
        form.onsubmit =(event)=>editTodo(event);   
        const inputs = formFields(form,inputName);
        const {description:descripcionField,id:idField,title:titleField,state:stateField,endDate:endDateField} = inputs;
        descripcionField.value = description
        idField.value = id
        titleField.value = title
        stateField.value = state
        endDateField.value = endDate

        myModal.show(); 
    });
}

function openModalAdd (){
    const form:HTMLElement = document.getElementById("form_modal_addTodo")!;   
    form.onsubmit =(event)=>addTodo(event);   
    myModalAdd.show(); 
}

// Función para editar un todo
async function editTodo(event:SubmitEvent) {
    event.preventDefault();
    try {
        const form:HTMLElement = document.getElementById("form_modal_editTodo")!;        
        const inputs = formFields(form,inputName);
        const {description,endDate,id,state,title} = inputs;
        

        const response = await fetch(`/todos/${id.value}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                description:description.value,
                endDate:endDate.value,
                state:state.value,
                title:title.value
            })
        });

        const result:any = await response.json();
        
        if (result.error) {
            const errors = JSON.parse(result.message);
            const message = Object.values(errors).filter(Boolean).join('\n');
            toastify({
                text: message,
                ...CONFIG,
                style: TOAST_STYLES.error
            });
        } else {
            const updateStates = result.data.split(",")
            updateStates.forEach((state:StateEnum)=>{
                reloadPage({state_reload:state})
            })
            toastify({
                text: "Todo updated successfully!",
                ...CONFIG,
                style: TOAST_STYLES.success
            });
            // clearInputsForms(inputs);
            myModal.hide(); 
            
        }
    } catch (error) {
        console.error("Error:", error);
        toastify({
            text: "An error occurred while updating the todo",
            ...CONFIG,
            style: TOAST_STYLES.error
        });
    }
}

// Función para verificar los inputs
function verifyInput<ObjectType, T extends keyof ObjectType>(inputs:Record<T, formFieldsType>) {
    let isError: boolean = false;
    (Object.entries(inputs) as [T, formFieldsType][]) .forEach(
        ([_, value])=>{
            const validateForm = value.dataset.validate; 
            if(validateForm){
                const errors = validationsInputs(validateForm,value);
                if(errors.length > 0){
                    isError = true;
                    errors.forEach((error)=>toastify({...CONFIG, text:error}))
                }
            }
        }
    )
    return isError;
}

// Función para recargar la página
function reloadPage({state_reload}:{state_reload:StateEnum}) {
    if(state_reload){
        const bodyState = document.getElementById("state_"+state_reload)!;
        bodyState.innerHTML = "";
        fetch(`/todos/state/${state_reload}`,{
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => response.json())
        .then((response:Response<TodoType[]>)=> {
            response.data.forEach(todo => {
                const optionsProps:optionsProps_type<any>[] = [
                    {title:'Edit',onclickFunction:`openModalEdit('${todo.id}')`},
                    {title:'delete',onclickFunction:  `confirmDeleteTodo('${todo.id}','${todo.state}')`}
                ]
                const dropdownMenuBtnProps:dropdownMenuBtnProps_type = {
                    title:todo.title,
                    moreStyle:""
                }
                bodyState.innerHTML += `
                <li id="${'tr_'+todo.id}" class="border border-1 border-primary w-75 mx-auto my-2 px-2">
                    <div class="d-flex justify-content-between">
                        <h4 class="w-75 text-center">${todo.title}</h4>
                        <div class="dropdown d-flex justify-content-end">
                            ${dropdownMenu_body({labelledby:"exampleModalLabel",dropdownMenuBtnProps,optionsProps})}
                        </div>
                    </div>
                    <p>${todo.description}</p>
                    <em class="d-flex justify-content-end text-muted"><time datetime="${todo.createdAt}" class="timeago"></time></em>
                </li>
                `;
            });
            timeago();
        });
    }
}

// Función para agregar un todo
function addTodo(event:SubmitEvent) {
    debugger;
    event.preventDefault();
    const form:HTMLElement = document.getElementById("form_modal_addTodo")!;    
    const dataForm:Record<formAdd, formFieldsType> = formFields(form,inputNameAdd);
    const {description,endDate,title} = dataForm;
    if (!verifyInput(dataForm)) {
        const createdAt = new Date();
        fetch("/todos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: title.value,
                description: description.value,
                endDate: endDate.value,
                createdAt
            })
        })
        .then(response => response.json())
        .then(data => {
            const config_toast = {
                text: `${data.message}`,
                ...CONFIG,
                style: {
                    background: "linear-gradient(to right,rgb(80, 204, 8), #96c93d)",
                }
            }
            
            reloadPage({state_reload:"BACKLOG"});
            toastify(config_toast);
            clearInputsForms(dataForm);
            myModalAdd.hide();
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }
}

// Función para confirmar la eliminación de un todo
function confirmDeleteTodo(id:string, state:StateEnum) {
    if(confirm("Are you sure you want to delete this todo?")){
        deleteTodo(id, state);
    }
}

// Función para eliminar un todo
function deleteTodo(id:string, state:StateEnum) {
    fetch(`/todos/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if(response.ok){
            const config_toast = {
                text: `Todo deleted successfully`,
                ...CONFIG,
                style: {
                    background: "linear-gradient(to right,rgb(80, 204, 8), #96c93d)",
                }
            };
            reloadPage({state_reload: state}); 
            toastify(config_toast);
        }
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

timeago();

const myModalElement = document.getElementById('exampleModal')!;

myModalElement.addEventListener('hidden.bs.modal', function () {
    const form:HTMLElement = document.getElementById("form_modal_editTodo")!;    
    const dataForm = formFields(form,inputName);
    clearInputsForms(dataForm);
});

(window as any).openModalEdit = openModalEdit;
(window as any).editTodo = editTodo;
(window as any).confirmDeleteTodo = confirmDeleteTodo;
(window as any).addTodo = addTodo;
(window as any).openModalAdd = openModalAdd;
(window as any).myModalAdd = myModalAdd;



