SPOTIFY_BASE_URL = 'https://api.spotify.com/v1'
CURRENT_PLAYBACK_URL = SPOTIFY_BASE_URL + "/me/player";
GET_LYRICS_URL = 'http://localhost:8080/lyrics/get'

const currentState = {
    id: "",
    track: "",
    artist: "",
    album: "",

    lastPrintedString: "",
    currentTimeMs: 0,
    lyrics: {}
}

$(document).ready(function () {
    init()
})

const init = () => {
    const token = Cookies.get('accessToken');
    setInterval(() => {
        getPlaybackState(token)
    }, 300)
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
            if (data && data.item.id !== currentState.id) {
                updateTrackInfo(data)
            }

            currentState.currentTimeMs = data.progress_ms

            const text = findCurrentString()

            if (text && currentState.lastPrintedString !== text.text && currentState.currentTimeMs >= text.startTime) {
                currentState.lastPrintedString = text.text;
                console.log(text.text)
            }
        }
    });
}

const findCurrentString = () => {
    if (!currentState.lyrics || !currentState.lyrics.syncedLyrics) {
        return null;
    }

    let low = 0;
    let high = currentState.lyrics.syncedLyrics.length - 1;
    while (low <= high) {
        let mid = Math.floor((low + high) / 2);

        if (currentState.lyrics.syncedLyrics[mid].milliseconds <= currentState.currentTimeMs) {
            if (mid === currentState.lyrics.syncedLyrics.length - 1 || currentState.lyrics.syncedLyrics[mid + 1].milliseconds > currentState.currentTimeMs) {
                const nextString = currentState.lyrics.syncedLyrics[mid + 1]

                return {
                    text: currentState.lyrics.syncedLyrics[mid].text,
                    startTime: currentState.lyrics.syncedLyrics[mid].milliseconds,
                    nextStartTime: nextString ? nextString.milliseconds : null
                };
            }

            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return null;
}

const updateTrackInfo = (data) => {
    currentState.track = data.item.name
    currentState.album = data.item.album.name
    currentState.id = data.item.id

    const artistNames = data.item.artists.map(artist => artist.name).join(',');
    currentState.artist = artistNames
    getLyrics()

    console.log(currentState)
}

const getLyrics = () => {
    $.ajax({
        url: GET_LYRICS_URL + `?trackName=${currentState.track}&artistName=${currentState.artist}&albumName=${currentState.album}`,
        method: 'get',
        contentType: 'application/json',
        processData: false,
        success: function (data) {
            currentState.lyrics = data
            console.log(data)
        }
    });
}
