$(document).ready(function () {
    init()
})

currentlyDisplayedTrackId = ""
currentlyHighlightedStringId = -1

LYRICS_STATE = {
    NoLyrics: "no-lyrics",
    ShowPlainLyrics: "show-plain-lyrics",
    ShowSyncedLyrics: "show-synced-lyrics",
    Loading: "loading",
    ShowAd: "loading"
}

const init = () => {
    const token = Cookies.get('accessToken');
    const spotify = new Spotify(token)
    const lyrics = new Lyrics()

    setInterval(() => {
        const spotifyData = spotify.getPlaybackState()
        const lyricsData = lyrics.cache

        if (currentlyDisplayedTrackId !== lyricsData.trackId || currentlyDisplayedTrackId === "" && spotifyData) {
            lyrics.loadNewLyrics(spotifyData)
            displayTrackInfo(spotifyData)
            setLyricsState(LYRICS_STATE.Loading)
        }

        const lyricsStringId = lyrics.findCurrentString(lyricsData.loadedLyrics, spotifyData.currentTimeMs)
        if (lyricsStringId !== currentlyHighlightedStringId) {
            scrollToLine(lyricsStringId)
        }
    }, 300)

    $(spotify).on('track-changed', function (event, state) {
        lyrics.loadNewLyrics(state)
        displayTrackInfo(state)
        setLyricsState(LYRICS_STATE.Loading)
    }).on('ad-playing', function (event, state) {
        showAdInfo()
        setLyricsState(LYRICS_STATE.ShowAd)
    }).on('play', function (event, state) {
        displayTrackInfo(state)
    }).on('pause', function (event, state) {
        displayTrackInfo(state)
    })

    $(lyrics).on('lyrics-loaded', function (event, lyrics) {
        displayLyrics(lyrics)
        setLyricsState(LYRICS_STATE.ShowLyrics)
    }).on('loading-error', function () {
        setLyricsState(LYRICS_STATE.NoLyrics)
    })
}

function scrollToLine(lineNumber) {
    const line = $('#' + lineNumber)
    window.location.hash = '#' + lineNumber
    const currentLine = $('.current-line')
    currentLine.removeClass('current-line')

    line.addClass('current-line')
}

const displayNoLyrics = () => {

}

const displayLyrics = (lyricsData) => {
    currentlyDisplayedTrackId = lyricsData.trackId
    currentlyHighlightedStringId = -1

    if (lyricsData.loadedLyrics.syncedLyrics) {
        setLyricsState(LYRICS_STATE.ShowSyncedLyrics)
    } else {
        setLyricsState(LYRICS_STATE.ShowPlainLyrics)
    }

    const lyricsWrapper = $('.lyrics-wrapper')
    lyricsWrapper.empty()

    const lyricsList = lyricsData.loadedLyrics.syncedLyrics ?? lyricsData.loadedLyrics.plainLyrics
    for (var i = 0; i < lyricsList.length; i++) {
        const span = $('<span />')
            .attr('class', 'lyrics-line lyrics-text')
            .attr('id', i)
            .html(lyricsList[i].text)

        lyricsWrapper.append(span)
    }

    window.location.hash = '#' + 0
}

const displayLyricsLoading = () => {
    const lyricsWrapper = $('.lyrics-wrapper')
    lyricsWrapper.empty()
}

const displayTrackInfo = (spotifyData) => {
    $('.track-name').text(spotifyData.track)
    $('.track-artists').text(spotifyData.artist)
    $('.cover-container').prop('show', true)

    const cover = spotifyData.coverLinks.filter(e => e.width < 512)[0] ?? spotifyData.coverLinks[0]
    const coverBackgroundAttribute = `background-image: url('${cover.url}')`
    $('.cover-container').attr('style', coverBackgroundAttribute)

    $('.play-pause-button').attr('data-state', spotifyData.isPlaying ? 'play' : 'pause')
}

const showAdInfo = () => {
    $('.track-name').text('Реклама')
    $('.track-artists').text('Spotify')
    $('.cover-container').prop('show', false)
}

const setLyricsState = (state) => {
    $('.lyrics-section').attr('data-state', state)
}