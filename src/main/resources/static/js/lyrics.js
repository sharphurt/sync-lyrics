class Lyrics {

    GET_LYRICS_URL = 'http://localhost:8080/lyrics/get'

    currentState = 'idle'

    cache = {
        loadedLyrics: {},
        trackId: "",
    }

    loadNewLyrics = (data) => {
        $.ajax({
            url: this.GET_LYRICS_URL + `?trackName=${data.track}&artistName=${data.artist}&albumName=${data.album}`.replaceAll('[', ' ').replaceAll(']', ' '),
            method: 'get',
            contentType: 'application/json',
            processData: false,
            success: (response) => this.onSuccessLoading(response, data.id),
            error: this.onError
        })
    }

    onSuccessLoading = (data, trackId) => {
        this.cache.loadedLyrics = data
        this.cache.trackId = trackId
        this.currentState = 'updated'
        $(this).trigger('lyrics-loaded', this.cache)
    }

    onError = (error) => {
        this.currentState = 'error'
        console.error(error)
        $(this).trigger('loading-error')
    }

    findCurrentString = (lyrics, time) => {
        if (!lyrics || !lyrics.syncedLyrics) {
            return null;
        }

        let low = 0;
        let high = lyrics.syncedLyrics.length - 1;
        while (low <= high) {
            let middle = Math.floor((low + high) / 2);

            if (lyrics.syncedLyrics[middle].milliseconds <= time) {
                if (middle === lyrics.syncedLyrics.length - 1 || lyrics.syncedLyrics[middle + 1].milliseconds > time) {
                    return middle;
                }

                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }

        return null;
    }
}