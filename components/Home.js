import React from 'react'

const axios = require('axios');


const Home = React.createClass({
    getInitialState() {
        return {memes: [], memePage: 0, message: ''}
    },

    getDefaultProps() {
        return {token: 'NO_TOKEN'}
    },

    componentDidMount() {
        this.pullMemes();
    },

    pullMemes() {
        console.log('DESCARGANDO MEMES');
        const {memes = [], memePage} = this.state;
        console.log('memes iniciales: ' + JSON.stringify(memes));
        const url = `api/memes?page=${memePage}`;
        axios.request({
            url: url,
            headers: {'Authorization': `Bearer ${this.props.token}`},
        }).then(response => {
            console.log("response.data: " + JSON.stringify(response.data));
            response.data.forEach(m => memes.push(m));
            this.setState({memes});
        }).catch(error => {
            if (error.response) this.setState({message: error.response.data.message});
            else this.setState({message: error.message});
            console.error(error);
        });
    },

    render() {
        const memeCardsStyle = {
            marginLeft: 'auto',
            marginRight: 'auto',
            maxWidth: '400px',
            marginBottom: '10px'
        };

        const memeCards = this.state.memes.map(meme => {
            const src = 'img/' + meme.imgName;
            return (
                <div className="card" style={memeCardsStyle}>
                    <img className="card-img-top" src={src} alt="Card image" style={{width: "100%"}}/>
                    <div className="card-body">
                        <h4 className="card-title">{meme.title}</h4>
                        <p className="card-text"><strong>{meme.username}</strong></p>
                        <p className="card-text">{meme.description}</p>
                        <button type={"button"} className="btn btn-default"><i className="far fa-thumbs-up"/></button>
                        <span style={{marginLeft: '10px'}}>{meme.upvotes}</span>
                    </div>
                </div>
            );
        });

        return (
            <div className={"container"}>
                <h1>Manteniendola real</h1>
                <h2>Los memes mas dank del oneverso</h2>
                <p>Subi y comparti todo lo incorrecto que se te ocurra papa</p>

                <p className="text-danger">{this.state.message}</p>

                <div className={"container"}>
                    {memeCards}
                </div>
            </div>
        );
    }
});

export default Home
