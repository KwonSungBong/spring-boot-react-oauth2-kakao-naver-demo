import React, { Component } from 'react';
import { ACCESS_TOKEN, REFRESH_TOKEN, TOKEN_TYPE, EXPIRY_DURATION } from '../../constants';
import { Redirect } from 'react-router-dom'

class OAuth2RedirectHandler extends Component {
    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        var results = regex.exec(this.props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    render() {        
        const accessToken = this.getUrlParameter('accessToken');
        const refreshToken = this.getUrlParameter('refreshToken');
        const tokenType = this.getUrlParameter('tokenType');
        const expiryDuration = this.getUrlParameter('expiryDuration');
        const error = this.getUrlParameter('error');

        if(accessToken) {
            localStorage.setItem(ACCESS_TOKEN, accessToken);
            localStorage.setItem(REFRESH_TOKEN, refreshToken);
            localStorage.setItem(TOKEN_TYPE, tokenType);
            localStorage.setItem(EXPIRY_DURATION, expiryDuration);
            return <Redirect to={{
                pathname: "/profile",
                state: { from: this.props.location }
            }}/>; 
        } else {
            return <Redirect to={{
                pathname: "/login",
                state: { 
                    from: this.props.location,
                    error: error 
                }
            }}/>; 
        }
    }
}

export default OAuth2RedirectHandler;