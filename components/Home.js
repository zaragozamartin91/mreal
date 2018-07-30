import React from 'react'

const Home = React.createClass({
    getInitialState() {
        return {memes: [], memePage: 0}
    },

    getDefaultProps() {
        return {token: 'NO_TOKEN'}
    },

    componentDidMount() {
        const {memes, memePage} = this.state;
        const url = `api/memes?page=${memePage}`;
        axios.request({

        })
    },

    render() {
        const memeContainers = this.state.memes.map(meme => (
            <div className="card" style="width:400px">
                <img className="card-img-top" src="img_avatar1.png" alt="Card image" style="width:100%"/>
                <div className="card-body">
                    <h4 className="card-title">John Doe</h4>
                    <p className="card-text">Some example text some example text. John Doe is an architect and
                        engineer</p>
                    <a href="#" className="btn btn-primary">See Profile</a>
                </div>
            </div>

        ));

        return (
            <div className={"container"}>
                <h1>Manteniendola real</h1>
                <h2>Los memes mas dank del oneverso</h2>
                <p>Subi y comparti todo lo incorrecto que se te ocurra papa</p>

                {memeContainers}
            </div>
        );
    }
});

export default Home
