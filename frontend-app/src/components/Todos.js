import React, { useContext, useEffect, useState } from 'react'
import { CredentialsContext } from '../App';
import NoteService from "../services/note.service";


export default function Todos() {
    const [todos, setTodos] = useState([{}]);
    const [todoText, setTodoText] = useState("");
    const [credentails] = useContext(CredentialsContext);

    const newTodo = {checked: false, text:todoText};

    useEffect(() => {
        list();
    }, []);

    const addTodo = (e) => {
        e.preventDefault();
        if (!todoText) return;
        add(todoText);
        setTodoText("");
    }

    const list = () => {
        NoteService.list().then((response) => {
            setTodos(response);
        });
    }

    const add = (text) => {
        NoteService.insert(text).then((response) => {
            const newTodo = { id: response.id, checked: false, text: todoText };
            const newTodos = [...todos, newTodo];
            setTodos(newTodos);
        });
    }

    const toggleTodo = (id) => {
        const newTodoList = [...todos];
        const item = findArrayElementById(newTodoList, id)
        if (item.status === 'O') {
            item.status = 'C';
        } else {
            item.status ='O';
        }
        NoteService.update(item.id, item.text, item.status);
        setTodos(newTodoList);
    }

    const deleteNote = (id, index) => {  
        const newList = [...todos];
        newList.splice(index, 1);
        NoteService.deleteNote(id);
        setTodos(newList);
    }

    function findArrayElementById(array, id) {
        return array.find((element) => {
          return element.id === id;
        })
      }

      return (
        <div>
            <form onSubmit={addTodo}>
                <input value={todoText} onChange={(e) => setTodoText(e.target.value)} type="text"></input>
                <button id="submitButton" type="submit">Add</button>
            </form>
            {todos.map((todo, index) => (
                <div key={index}>
                    <input
                        defaultChecked={todo.status === 'C'} 
                        onChange={() => toggleTodo(todo.id)} 
                        id={todo.id}
                        type="checkbox"/>
                    <label>{todo.text}</label>
                    <button type="button" onClick={() => deleteNote(todo.id, index)} id={"delete" + todo.id} class="btn btn-secondary"><i class="bi bi-trash"></i></button>
                </div>
            ))}
            
        </div>
    )
}
