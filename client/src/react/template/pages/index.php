
<div class="h1">Добрый день</div>
<div dynamic-page-content="index"></div>

<script>
    class PageIndex extends Component {

        render = () => {
            return new Promise((resolve, reject) => {

                setTimeout(() => {
                    this.getDynamicContent().innerHTML = 'Content loaded'
                    resolve()
                }, 1000)

            })
        }

    }
</script>
