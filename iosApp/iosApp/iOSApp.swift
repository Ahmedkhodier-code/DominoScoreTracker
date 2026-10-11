import SwiftUI
import sharedUI

@main
struct iOSApp: App {
    init() {
        KoinHelper().initKoinForIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
