import React from 'react'

const PostForm = React.createClass({
    getDefaultProps() {
        return {
            uploadType: 'meme'
        }
    },

    submitForm(e) {
        e.preventDefault();
        const imageInput = document.querySelector("#imageInput");
        console.log("subiendo: " + JSON.stringify(imageInput));
    },

    render() {
        return (
            <div className={"container"}>
                <h1>Sube un post</h1>
                <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                    <label className="form-check-label" htmlFor="inlineRadio1">meme</label>
                </div>
                <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"/>
                    <label className="form-check-label" htmlFor="inlineRadio2">post</label>
                </div>

                <form encType="multipart/form-data">
                    <div className="form-group">
                        <label htmlFor="title">Titulo</label>
                        <input type="text" className="form-control" id="title" placeholder="Titulo del post"/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="imageInput">Sube una imagen</label>
                        <input type="file" name={"image"} accept="image/*" className="form-control-file" id="imageInput"/>
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