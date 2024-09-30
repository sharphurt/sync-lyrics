SPOTIFY_BASE_URL = 'https://api.spotify.com/v1'
CURRENT_PLAYBACK_URL = SPOTIFY_BASE_URL + "/me/player";

$(document).ready(function() {
    init()
})

const init = () => {
    const token = Cookies.get('accessToken');
    setInterval(() => {
        getPlaybackState(token)
    }, 100)
}

const getPlaybackState = (accessToken) => {
    $.ajax({
        url: CURRENT_PLAYBACK_URL,
        method: 'get',
        dataType: 'json',
        headers: {
            "Authorization": "Bearer " + accessToken
        },
        success: function (data) {
            console.log(data.progress_ms)
        }
    });
}
