import SwiftUI
import SharedUI

@main
struct iOSApp: App {
    init() {
        InitKoinUiKt.initKoinForIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
