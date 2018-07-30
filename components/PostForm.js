import React from 'react'

const axios = require('axios');

const PostForm = React.createClass({
    getDefaultProps() {
        return {token: 'NO_TOKEN', username: 'NO_USERNAME'}
    },

    getInitialState() {
        return {title: '', message: '', succMsg: '', description: ''}
    },

    submitForm(e) {
        e.preventDefault();
        const imageInput = document.querySelector("#imageInput");
        const formData = new FormData();
        formData.append(imageInput.name, imageInput.files[0]);
        formData.append('title', this.state.title);
        formData.append('description', this.state.description);
        formData.append('username', this.props.username);

        console.log("subiendo meme: " + this.state.title);
        console.log(imageInput.files[0]);

        const url = `api/meme`;
        console.log("url: " + url);

        axios.request({
            url,
            method: 'post',
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization': `Bearer ${this.props.token}`
            },
            data: formData
        }).then(response => {
            this.setState({succMsg: response.data.message});
        }).catch(error => {
            if (error.response) this.setState({message: error.response.data.message});
            else this.setState({message: error.message});
            console.error(error);
        });
    },

    componentDidMount() {
        console.log("PostForm creado, token = " + this.props.token);
    },

    render() {
        return (
            <div className={"container"}>
                <h1>Sube un post</h1>

                <p className="text-danger">{this.state.message}</p>
                <p className="text-success">{this.state.succMsg}</p>

                <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1"
                           value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">meme</label>
                </div>
                <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2"
                           value="option2"/>
                    <label className="form-check-label" htmlFor="inlineRadio2">post</label>
                </div>

                <form encType="multipart/form-data">
                    <div className="form-group">
                        <label htmlFor="title"
                               value={this.state.title}>Titulo</label>
                        <input
                            type="text"
                            className="form-control"
                            id="title"
                            placeholder="Titulo del post"
                            onChange={e => this.setState({title: e.target.value})}/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="description"
                               value={this.state.description}>Titulo</label>
                        <input
                            type="text"
                            className="form-control"
                            id="description"
                            placeholder="Comentario del post"
                            onChange={e => this.setState({description: e.target.value})}/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="imageInput">Sube una imagen</label>
                        <input type="file" name={"image"} accept="image/*" className="form-control-file"
                               id="imageInput"/>
                    </div>
                    <div className="form-group">
                        <button onClick={this.submitForm} className="btn btn-primary">Subir</button>
                    </div>
                </form>
            </div>
        );
    }
});

export default PostForm