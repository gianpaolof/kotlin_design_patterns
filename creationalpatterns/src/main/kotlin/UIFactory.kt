interface View

abstract class Button : View
abstract class Image : View
abstract class GridView : View

// Abstract Factory
interface UIElementFactory {
    fun createButton(): Button
    fun createImage(): Image
    fun createGridView(): GridView

    enum class THEME {
        Dark, Light
    }

//    companion object {
//        // "static" method that supplies concrete factory
//        fun createFactory(th: THEME): UIElementFactory {
//            return when (th) {
//                THEME.Dark -> DarkThemeUIElementFactory()
//                THEME.Light -> LightThemeUIElementFactory()
//            }
//        }
//    }
}
// class hiding concrete factories, but implementing the same interface
class ViewCreator(th: UIElementFactory.THEME) : UIElementFactory {

    // instance of `ViewCreator` can provide controls only for provided in the constructor OS
    private val factory = when (th) {
        UIElementFactory.THEME.Dark -> DarkThemeUIElementFactory()
        UIElementFactory.THEME.Light -> LightThemeUIElementFactory()
    }

    // methods returning generic controls from the factory
    // created on the constructor argument base
    override fun createButton(): Button = factory.createButton()
    override fun createImage(): Image = factory.createImage()
    override fun createGridView(): GridView = factory.createGridView()
}

// Concrete Factories (e.g., for different themes or platforms)
internal class DarkThemeUIElementFactory : UIElementFactory {
    override fun createButton(): Button = DarkThemeButton()
    override fun createImage(): Image = DarkThemeImage()
    override fun createGridView(): GridView = DarkThemeGridView()
}

internal class LightThemeUIElementFactory : UIElementFactory {
    override fun createButton(): Button = LightThemeButton()
    override fun createImage(): Image = LightThemeImage()
    override fun createGridView(): GridView = LightThemeGridView()
}

// Concrete Products
internal class DarkThemeButton : Button() { /* ... implementation ... */ }
internal class DarkThemeImage : Image() { /* ... implementation ... */ }
internal class DarkThemeGridView : GridView() { /* ... implementation ... */ }

internal class LightThemeButton : Button() { /* ... implementation ... */ }
internal class LightThemeImage : Image() { /* ... implementation ... */ }
internal class LightThemeGridView : GridView() { /* ... implementation ... */ }
