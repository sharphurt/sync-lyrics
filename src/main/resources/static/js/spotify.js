class Spotify {
    SPOTIFY_BASE_URL = 'https://api.spotify.com/v1'
    CURRENT_PLAYBACK_URL = this.SPOTIFY_BASE_URL + "/me/player";
    REQUEST_PERIOD_MS = 300

    currentState = {
        currentlyPlayingType: "",
        isPlaying: false,

        id: "",
        track: "",
        artist: "",
        album: "",
        coverLinks: [],
        currentTimeMs: 0,
    }

    isSuccessFetch = false

    constructor(accessToken) {
        this.token = accessToken

        setInterval(() => {
            $.ajax({
                url: this.CURRENT_PLAYBACK_URL,
                method: 'get',
                dataType: 'json',
                headers: {
                    "Authorization": "Bearer " + this.token
                },
                success: this.updateTrackInfo,
                error: this.errorHandler
            });
        }, this.REQUEST_PERIOD_MS)
    }

    getPlaybackState = () => {
        return this.isSuccessFetch ? this.currentState : null
    }

    updateTrackInfo = (data) => {
        if (!data) {
            this.isSuccessFetch = false
            return
        }

        this.isSuccessFetch = true

        const newState = {
            currentlyPlayingType: "",
            isPlaying: false,

            id: "",
            track: "",
            artist: "",
            album: "",
            coverLinks: [],
            currentTimeMs: 0,
        }

        newState.currentlyPlayingType = data.currently_playing_type
        newState.isPlaying = data.is_playing

        newState.track = data.item?.name ?? ''
        newState.album = data.item?.album.name ?? ''
        newState.id = data.item?.id ?? ''
        newState.artist = (data.item?.artists ?? []).map(artist => artist.name).join(', ');
        newState.currentTimeMs = data.progress_ms ?? 0
        newState.coverLinks = data.item?.album.images

        this.fireEvents(newState)
        this.currentState = newState
    }

    fireEvents = (newState) => {
        if (this.currentState.isPlaying !== newState.isPlaying) {
            newState.isPlaying ? $(this).trigger('play', newState) : $(this).trigger('pause', newState)
            return
        }

        if (this.currentState.currentlyPlayingType !== 'ad' && newState.currentlyPlayingType === 'ad') {
            $(this).trigger('ad-playing', newState)
            return
        }

        if (newState.currentlyPlayingType === 'track' && this.currentState.id !== newState.id) {
            $(this).trigger('track-changed', newState)
            return
        }
    }

    errorHandler = (error) => {
        console.error(error)
        this.isSuccessFetch = false
    }
}